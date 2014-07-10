(ns clojure-webapp.core
  (:require [clojure-webapp.handlers :as handlers]
            [ring.middleware.resource :as resource]
            [ring.middleware.file-info :as file-info]
            [clojure.string]) )

(defn case-middleware [handler request]
  (let [request (update-in request [:uri] clojure.string/lower-case)
        response (handler request)]
    (if (string? (:body response))
      (update-in response [:body] clojure.string/capitalize)
      response)))

(defn wrap-case-middleware [handler]
  (fn [request] (case-middleware handler request)))

(defn exception-middleware-fn [handler request]
  (try (handler request)
    (catch Throwable e
      {:status 500
       :body (apply str (interpose "\n" (.getStackTrace e)))})))

(defn wrap-exception-middleware [handler]
  (fn [request]
    (exception-middleware-fn handler request)))

(defn not-found-middleware [handler]
  (fn [request]
    (or (handler request)
        {:status 404
         :body (str "404 Not found (with middleware): " (:uri request))})))

(defn simple-log-middleware [handler]
  (fn [{:keys [uri] :as request}]
    (println "Request Path " uri)
    (handler request)))

(defn example-handler [request]
  {:headers {"Location" "https://github.com/robinsonraju/clojure-webapp"
             "Set-cookie" "test=1"}
   :status 301})

(defn on-init []
  (println "Initializing sample web app!"))

(defn on-destroy []
  (println "Closing sample web app."))

(defn test1-handler [request]
  (throw (RuntimeException. "error!"))
  {:body "test1"})

(defn test2-handler [request]
  {:status 301 :headers {"Location" "https://github.com/robinsonraju/clojure-webapp"}})

(defn route-handler [request]
  (condp = (:uri request)
    "/test1" (test1-handler request)
    "/test2" (test2-handler request)
    "/test3" (handlers/test3-handler request)
    nil))

(defn wrapper-handler [request]
  (try
  (if-let [resp (route-handler request)]
    resp
    {:status 404 :body (str "Not found " (:uri request))})
  (catch Throwable e
    {:status 500 :body (apply str (interpose "/n" (.getStackTrace e)))}))
  )

(def full-handler
  (-> route-handler
      not-found-middleware
      (resource/wrap-resource "public")
      file-info/wrap-file-info
      wrap-case-middleware
      wrap-exception-middleware
      simple-log-middleware))
