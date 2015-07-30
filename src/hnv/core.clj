(ns hnv.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json])
  (:use [clojure.tools.namespace.repl :only [refresh]]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

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

(defn format-json [{:keys [by score time title url]}]
  [by score time title url])
