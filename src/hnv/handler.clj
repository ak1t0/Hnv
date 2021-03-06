(ns hnv.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [hnv.html :refer [index top-viewer quality-viewer latest-viewer]])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] (index))
  (GET "/tops" [] (top-viewer))
  (GET "/scores" [] (quality-viewer))
  (GET "/latest" [] (latest-viewer))

  ;;(GET "/resources/css/:file" [file]
  ;;  (io/file (str "resources/css/" file)))
  ;;(GET "/resources/js/:file" [file]
  ;;  (io/file (str "resources/js/" file)))
  ;;(GET "/resources/font/:dir/:file" [dir file]
  ;;  (io/file (str "resources/font/" dir "/" file)))
  (GET "/resources/favicon.ico" []
    (io/file "resources/favicon.ico")))

(def app (handler/site app-routes))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (handler/site #'app) {:port port :join? false})))
