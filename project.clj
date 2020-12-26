(defproject ukrainian_cases "0.2.0-SNAPSHOT"
  :description "Project to get all the cases forms for a given ukrainian word"
  :url "https://github.com/luifa7/ukrainian-cases"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-devel "1.2.2"]
                 [ring-basic-authentication "1.0.5"]
                 [environ "1.2.0"]
                 [com.cemerick/drawbridge "0.0.6"]
                 [cheshire "5.10.0"]]
  :min-lein-version "2.0.0"
  :plugins [[lein-ring "0.12.5"]
            [environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :ring {:handler ukrainian-cases.handler/app}
  :main ukrainian-cases.core
  :aot [ukrainian-cases.core]
  :target-path "target/%s"
  :uberjar-name "ukrainian-cases-standalone.jar"
  :profiles {:production {:env {:production true}
                          :dependencies [[javax.servlet/servlet-api "2.5"]
                                         [ring/ring-mock "0.3.2"]]}}
  )
