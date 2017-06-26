(ns machine-learning-clojure.core
  (:gen-class)
  (:use clojure.core.matrix
        [incanter.charts :only [xy-plot add-points]]
        [incanter.core   :only [view]])
  (:require [clatrix.core :as cl]
            [clojure.core.matrix.operators :as M]))

;; Examples from
;; Clojure for Machine Learning
;; by Akhil Wali

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

  ;;Chapter1 - Generating matrixes
(defn square-mat
  "Creates a square matrix of size n x n whose
  elements are all e. Accepts an option argument
  for the matrix implementation."
  [n e & {:keys [implementation]
          :or {implementation :persistent-vector}}]
  (let [repeater #(repeat n %)]
    (matrix implementation (-> e repeater repeater))))

(defn id-mat
  "Creates an identity matrix of n x n size"
  [n]
  (let [init (square-mat :clatrixn 0 )
        identity-f (fn [i j n]
                     (if (= i j) 1 n))]
    (cl/map-indexed identity-f init)))

(defn rand-square-mat
  "Generates a random matrix of size n x n"
  [n]
  (matrix
   (repeatedly n #(map rand-int (repeat n 100)))))

(defn rand-square-clmat
  "Generates a random clatrix matrix of size n x n"
  [n]
  (cl/map rand-int (square-mat :clatrix n 100)))

(defn id-computed-mat
  "Creates an identity matrix of size n x n
  using compute-matrix"
  [n]
  (compute-matrix [n n] #(if (= %1 %2) 1 0)))

(defn rand-computed-mat
  "Creates an n x m matrix of random elements
  using compute-matrix"
  [n m]
  (compute-matrix [n m]
                  (fn [i j] (rand-int 100))))

;;Chapter2 - Adding matrixes

;;same as M/==
(defn mat-eq
  "Checks if two matrices are equal"
  [A B]
  (and (= (count A) (count B))
       (reduce #(and %1 %2) (map = A B))))
;;same as M/+
(defn mat-add
  "Add two or more matrices"
  ([A B]
   (mapv #(mapv + %1 %2) A B))
  ([A B & more]
   (let [M (concat [A B] more)]
     (reduce mat-add M))))

(comment

  ;;Chapter1 - Representing matrices

  (matrix [[0 1 2] [3 4 5]])

  (cl/matrix [0 1])

  (cl/matrix [[0 1]])

  (def A (cl/matrix [[0 1]]))
  (def B (matrix [[0 1]]))

  ;;prints as vector of vectors
  (pm A)

  (matrix? A)

  (cl/clatrix? A)

  (cl/clatrix? B)

  (matrix :persistent-vector [[1 2] [2 1]])

  (matrix :clatrix [[1 2] [2 1]])

  (count (cl/matrix [0 1 2]))

  (row-count (cl/matrix [0 1 2]))

  (column-count (cl/matrix [0 1 2]))

 
  (def A (cl/matrix [[0 1 2] [3 4 5]]))

  (cl/get A 1 1)

  ;;row-first traversal
  (cl/get A 3)

  (cl/set A 1 2 0)

  (cl/map-indexed
   (fn [i j m] (* m 2)) A)

  ;;row
  (pm (cl/map-indexed (fn [i j m] i) A))

  ;;column
  (pm (cl/map-indexed (fn [i j m] j) A))

  ;;Chapter2 - Generating matrices
  (square-mat 2 1)

  ;;error?!?!?
  (id-mat 5)

  ;;core.matrix
  (pm (identity-matrix 5))

  (rand-square-mat 4)

  ;;normally distributed random elements
  ;;with optionally specified mean and standard deviations
  (cl/rnorm 10 25 10 10)

  ;;using compute-matrix
  (id-computed-mat 4)

  (rand-computed-mat 3 2)

  ;;Chapter3 - Adding matrixes

  ;;expected error
  (+ (matrix [[0 1]]) (matrix [[0 1]]))

  (M/+ (matrix [[0 1]]) (matrix [[0 1]]))

  (def A (matrix [[0 1 2] [3 4 5]]))

  (def B (matrix [[0 0 0] [0 0 0]]))

  (M/== B A)

  (def C (M/+ A B))

  (M/== C A)

  ;;Chapter4 - Multiply matrixes

  (def A (cl/matrix [[10 20 30] [20 30 40]]))

  (def B (cl/matrix [[1 4] [5 2] [6 3]]))

  (pm A)

  (pm B)


  ;;error?
  (pm (M/* A B))

  (def A (cl/matrix [[2 0] [0 2]]))

  (pm A)

  (pm (M/* A 10))

  (pm (scale A 10))

  (M/== (scale A 10) (M/* A 10))

  ;;matrix-vector multiplication

  ;;Chapter 1.5 - Transpose and Invert

  (def A (matrix [[1 2 3] [4 5 6]]))

  (pm A)

  (pm (transpose A))

  (def A (cl/matrix [[-2 2 3] [-1 1 3] [2 0 -1]]))

  (pm A)

  (det A)

  ;;expected error
  (pm (inverse A))

  (def A (cl/matrix [[2 0] [2 0]]))

  (pm (inverse A))

  (def A (cl/matrix [[2 1] [2 0]]))

  (pm A)

  (pm (inverse A))

  ;;strange
  (mmul (inverse A) A)

  )
