(ns hnv.core
  (:require [org.httpkit.client :as http]
            [clj-json.core :as json]
            [plumbing.core :refer [map-keys]])
  (:use [clojure.tools.namespace.repl :only [refresh]]))

(defn format-time [x]
  (let [t (-> (System/currentTimeMillis) (quot 1000) (- x) (/ 60))]
    (cond
      (< t 60) (str (int t) " mins")
      (< t 1440) (str (int (/ t 60)) " hours")
      :else (str (int (/ t 1440)) " days"))))

(defn take-body [x]
  (@x :body))

(defn take-json [obj]
  (json/parse-string (:body @obj)))

(defn take-json-k [obj]
  (map-keys keyword
    (json/parse-string (:body @obj))))

(defn gen-url [x]
  (str "https://hacker-news.firebaseio.com/v0/item/" x ".json"))

;; [id, id, id, ...]
(defn get-topstory []
  (-> "https://hacker-news.firebaseio.com/v0/topstories.json"
      (http/get)
      (take-json)))

(defn get-newstory []
  (-> "https://hacker-news.firebaseio.com/v0/newstories.json"
      (http/get)
      (take-json)))

;; take id number
(defn get-json [x]
  (-> (str "https://hacker-news.firebaseio.com/v0/item/" x ".json")
      (http/get)
      (take-json-k)))

(defn get-jsons [id]
  (->> id
    (map gen-url)
    (map http/get)
    (map take-json-k)))

(defn format-json [{:keys [by score time title url descendants]}]
  [by score (format-time time) title url descendants])
