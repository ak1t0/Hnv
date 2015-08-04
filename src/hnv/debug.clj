(ns hnv.debug
  (:require [hnv.html :as h]
            [hnv.core :as q]
            [clojure.core.reducers :as r])
  (:use [clojure.tools.namespace.repl :only [refresh]]
        [criterium.core :only [bench]]))
