(ns ukrainian-cases.core
  (:gen-class)
  (:use ring.adapter.jetty)
  (:require [ukrainian-cases.handler :refer [app] ])
  )

(defn -main [& args]
  (run-jetty app {:port 3000}))
