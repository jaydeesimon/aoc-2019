(ns aoc-2019.misc
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; lifted from xforms
;; https://github.com/cgrand/xforms/blob/master/src/net/cgrand/xforms/io.clj#L14
(defn lines-in
  "Returns a reducible view over the provided input.
   Input is read line by line. Coercion of the input is done by io/reader (and opts are passed to it).
   Input is automatically closed upon completion or error."
  [in & opts]
  (let [no-init (Object.)]
    (reify clojure.lang.IReduce
      (reduce [self f] (.reduce self f no-init))
      (reduce [self f init]
        (with-open [rdr (apply io/reader in opts)]
          (let [rdr  (cond-> rdr (not (instance? java.io.BufferedReader rdr)) java.io.BufferedReader.)
                init (if (identical? init no-init)
                       (or (.readLine rdr) (f))
                       init)]
            (loop [state init]
              (if-some [line (.readLine rdr)]
                (let [state (f state line)]
                  (if (reduced? state)
                    (unreduced state)
                    (recur state)))
                state))))))))

(defn- ns-right []
  (-> *ns* str (str/split #"\.") second))

(defn reducible-input []
  (lines-in (io/resource (str (ns-right) ".txt"))))

(defn slurped-input []
  (slurp (io/resource (str (ns-right) ".txt"))))

(defn parse-long [s]
  (Long/parseLong s))
