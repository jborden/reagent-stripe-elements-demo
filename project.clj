(defproject reagent-stripe-elements-demo "0.1.0-SNAPSHOT"
  :description "A demo for using reagent-stripe-elements"
  :url "http://jborden.github.io/reagent-stripe-elements"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.225"]
                 [cljsjs/react-stripe-elements "1.4.1-1"]
                 [reagent "0.7.0"]]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.14"]]
  :npm {:dependencies [[source-map-support "0.4.0"]]}
  :source-paths ["src" "target/classes"]
  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src"]
             :figwheel true
             :compiler {:main reagent-stripe-elements-demo.core
                        :asset-path "js/out"
                        :output-to "resources/public/js/reagent_stripe_elements_demo.js"
                        :output-dir "resources/public/js/out"
                        :source-map-timestamp true}}
            {:id "production"
             :source-paths ["src"]
             :compiler {:main reagent-stripe-elements-demo.core
                        :asset-path "/"
                        :output-to "resources/public/js/reagent_stripe_elements_demo.min.js"
                        :optimizations :advanced}}]}
  :figwheel {:css-dirs ["resources/public/css"]}
  :clean-targets ["out" "release"]
  :target-path "target")
