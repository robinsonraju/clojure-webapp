(ns clojure-webapp.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn example-handler [request]
  {:headers {"Location" "https://github.com/robinsonraju/clojure-webapp"
             "Set-cookie" "test=1"}
   :status 301})

(defn on-init []
  (println "Initializing sample web app!"))

(defn on-destroy []
  (println "Closing sample web app."))
