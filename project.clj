(defproject hnv "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.18"]
                 [clj-json "0.5.3"]
                 [prismatic/plumbing "0.4.4"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [compojure "1.4.0"]
                 [ring "1.4.0"]
                 [hiccup "1.0.5"]
                 [environ "1.0.0"]
                 [criterium "0.4.3"]]
  :plugins [[lein-ring "0.9.6"]
            [lein-environ "1.0.0"]]
  :repl-options
  {:init-ns hnv.core
   :prompt (fn [ns] (str ns " > " ))}
  ;; for ring
  :ring {:handler hnv.handler/app}
  :main hnv.handler
  :min-lein-version "2.0.0"
  :uberjar-name "hnv-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
