(ns hnv.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json])
  (:use [clojure.tools.namespace.repl :only [refresh]]))

(defn format-time [x]
  (let [t (-> (System/currentTimeMillis) (quot 1000) (- x) (/ 60))]
    (cond
      (< t 60) (str (int t) " mins")
      (< t 1440) (str (int (/ t 60)) " hours")
      :else (str (int (/ t 1440)) " days"))))

;; [id, id, id, ...]
(defn get-topstory []
  (-> "https://hacker-news.firebaseio.com/v0/topstories.json"
      (client/get)
      :body
      (json/read-str)))

;; take id number
(defn get-json [x]
  (-> (str "https://hacker-news.firebaseio.com/v0/item/" x ".json")
      (client/get)
      :body
      (json/read-str :key-fn keyword)))

(defn format-json [{:keys [by score time title url descendants]}]
  [by score (format-time time) title url descendants])
