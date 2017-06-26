(defproject machine-learning-clojure "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/core.matrix "0.20.0"]
                 [clatrix "0.3.0"]
                 [incanter "1.5.4"]]
  :main ^:skip-aot machine-learning-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
