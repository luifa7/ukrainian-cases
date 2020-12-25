(ns ukrainian-cases.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ukrainian-cases.cases.nominative :refer [get-possible-genders-nominative
                                                      get-possible-plurals-nominative]]
            [ukrainian-cases.util :refer [to-json]]))

(defroutes app-routes
  (context "/nouns" []
    (defroutes nouns-routes
     (GET  "/" [] "Hello Nouns")
     (context "/:noun" [noun]
      (defroutes noun-routes
       (GET  "/" [] noun)
       (GET  "/gender" [] (to-json (get-possible-genders-nominative noun)))
       (GET  "/nominative" [] (to-json (get-possible-plurals-nominative noun)))
       ))))

 (GET "/" [] "Hello World")
 (route/not-found "Not Found")
 )

(def app
  (wrap-defaults app-routes site-defaults))
