(ns leiningen.ruin
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- walk [dirpath pattern]
  (doall (filter #(re-matches pattern (.getName %))
                 (file-seq (io/file dirpath)))))

(defn- enum-files []
  (map #(.getPath %) (walk "src" #".*\.clj.*")))

(defn strip-parens [file-string]
  (str/replace file-string #"(\(|\)|\[|\]|\{|\})" ""))

(defn ruin
  "Ruin your project."
  [project & args]
  (doseq [f (enum-files)]
    (spit f (strip-parens (slurp f)))))
