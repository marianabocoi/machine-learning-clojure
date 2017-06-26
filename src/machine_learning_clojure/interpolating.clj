(ns machine-learning-clojure.interpolating
  (:use [clojure.core.matrix :only [compute-matrix pm matrix column-count row-count transpose inverse mmul]]
        [incanter.charts :only [xy-plot add-points]]
        [incanter.core   :only [view]])
  (:require [clojure.core.matrix.operators :as M]
            [clatrix.core :as cl]))

;; Examples from
;; Clojure for Machine Learning
;; by Akhil Wali

;;Chapter 1.6 - Interpolating using matrices

  (defn lmatrix [n]
    (compute-matrix :clatrix [n (+ n 2)]
                    (fn [i j] ({0 -1, 1 2, 2 -1} (- j i) 0))))

  (pm (lmatrix 4))

  ;;Tichonov regularization

  (defn problem
    "Return a map of the problem setup for a
  given matrix size, number of observed values 
  and regularization parameter"
    [n n-observed lambda]
    (let [i (shuffle (range n))]
      {:L (M/* (lmatrix n) lambda)
       :observed (take n-observed i)
       :hidden (drop n-observed i)
       :observed-values (matrix :clatrix
                                (repeatedly n-observed rand))}))
  ;;madeup example
  (problem 3 4 0.3)


  (defn solve
    "Return a map containing the approximated value 
y of each hidden point x"
    [{:keys [L observed hidden observed-values] :as problem}]
    (let [nc  (column-count L)
          nr  (row-count L)
          L1  (cl/get L (range nr) hidden)
          L2  (cl/get L (range nr) observed)
          l11 (mmul (transpose L1) L1)
          l12 (mmul (transpose L1) L2)]
      (assoc problem :hidden-values
             (mmul -1 (inverse l11) l12 observed-values))))

  (defn plot-points
    "Plots sample points of a solution s"
    [s]
    (let [X (concat (:hidden s) (:observed s))
          Y (concat (:hidden-values s) (:observed-values s))]
      (view
       (add-points
        (xy-plot X Y) (:observed s) (:observed-values s)))))

(defn plot-rand-sample []
  (plot-points (solve (problem 150 10 30))))

(plot-rand-sample)


