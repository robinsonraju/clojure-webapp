(ns clojure-webapp.core
  (:require [clojure-webapp.handlers :as handlers]) )

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

(defn test1-handler [request]
  {:body "test1"})

(defn test2-handler [request]
  {:status 301 :headers {"Location" "https://github.com/robinsonraju/clojure-webapp"}})

(defn route-handler [request]
  (condp = (:uri request)
    "/test1" (test1-handler request)
    "/test2" (test2-handler request)
    "/test3" (handlers/test3-handler request)
    nil))
