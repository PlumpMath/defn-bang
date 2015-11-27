(ns pure-fn.core
  (:require [clojure.walk :as walk]
            [clojure.set :as set]))

;; Try to determine if a function is pure or not, and make potential
;; optimizations

;; io -> do

;; What about transient operations?

;; What about implicit do in let

(def impure-fns
  '#{do dotimes dorun doall vswap! vreset! volatile!
     pr prn print printf println doto spit run!})

(defn all-symbols
  "Returns all the tokens used by a function"
  [form]
  (loop [form form
         acc #{}]
    (if (seq form)
      (if (coll? (first form))
        (recur (rest form) (set/union acc (all-symbols (first form))))
        (recur (rest form) (conj acc (first form))))
      acc)))

(defn impure? [form]
  (not (empty? (set/intersection impure-fns (all-symbols form)))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
