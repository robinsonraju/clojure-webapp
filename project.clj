(defproject clojure-webapp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
		[ring "1.3.0"]]

  :plugins [[lein-ring "0.8.11"]]

  :ring {:handler clojure-webapp.core/wrapper-handler
         :init clojure-webapp.core/on-init
         :destroy clojure-webapp.core/on-destroy})
