(defproject ukrainian_cases "0.2.0-SNAPSHOT"
  :description "Project to get all the cases forms for a given ukrainian word"
  :url "https://github.com/luifa7/ukrainian-cases"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [org.clojure/data.json "1.0.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler ukrainian-cases.handler/app}
  :main ukrainian-cases.core
  :aot [ukrainian-cases.core]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]]}}
  )
