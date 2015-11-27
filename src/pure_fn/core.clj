(ns pure-fn.core
  (:require [clojure.walk :as walk]))

;; Try to determine if a function is pure or not, and make potential
;; optimizations

;; io -> do

;; What about transient operations?

;; What about implicit do in let

(def impure-fns
  '#{do dotimes dorun doall vswap! vreset! volatile!
     pr prn print printf println doto spit run!})

(defn impure? [form]
  (walk/postwalk (fn [f]
                   (println f)
                   (contains? impure-fns f))
                 (macroexpand form)))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
