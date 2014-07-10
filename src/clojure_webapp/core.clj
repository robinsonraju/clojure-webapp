(ns clojure-webapp.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn example-handler [request]
  {:body "Hello Clojure!"})

(defn on-init []
  (println "Initializing sample web app!"))

(defn on-destroy []
  (println "Closing sample web app."))
