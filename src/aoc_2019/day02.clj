(ns aoc-2019.day02
  (:require [clojure.string :as str]
            [aoc-2019.misc :refer [parse-long slurped-input]]))

(defn parse-program [text]
  (as-> (str/split text #",") it
        (mapv parse-long it)))

(def program (parse-program (slurped-input)))

(defn run-program* [program]
  (loop [pos 0
         program program]
    (if (= (get program pos) 99)
      program
      (let [[opcode read1 read2 out] (subvec program pos (+ pos 4))
            val1 (get program read1)
            val2 (get program read2)
            op   (get {1 + 2 *} opcode)
            result (op val1 val2)]
        (recur (+ pos 4)
               (assoc program out result))))))

(defn run-program [program noun verb]
  (let [program (-> program
                    (assoc 1 noun)
                    (assoc 2 verb))]
    (first (run-program* program))))

(defn find-noun-verb [program target]
  (->> (for [x (range 100)
             y (range 100)]
         [x y])
       (some (fn [[noun verb]]
               (when (= (run-program program noun verb) target)
                 [noun verb])))))

(defn part01 []
  (run-program program 12 2))

(defn part02 []
  (let [[noun verb] (find-noun-verb program 19690720)]
    (+ (* 100 noun) verb)))

(comment

  (part01)

  (part02)

  )
