(ns ukrainian-cases.core
  (:gen-class)
  (:require [ukrainian-cases.handler :refer [app]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :as jetty])
  )

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app {:port port :join? false})))