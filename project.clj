(defproject hello-world "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring "1.3.2"]
                 [hiccup "1.0.5"]
                 [lib-noir "0.9.5"]
                 [clj-http "1.0.1"]
                 [ring/ring-defaults "0.1.3"]
                 [org.clojure/tools.namespace "0.2.8"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler hello-world.core.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
