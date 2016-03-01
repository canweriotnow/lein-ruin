(ns leiningen.ruin
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- enum-files []
  (let [wd (first (seq (.list (io/file "src"))))]
    (map #(.getPath %)
         (filter #(not (.isDirectory %))
                 (seq (.listFiles (io/file (str "src/" wd))))))))

(defn strip-parens [file-string]
  (str/replace file-string #"(\(|\)|\[|\])" ""))

(defn ruin
  "Ruin your project."
  [project & args]
  (doseq [f (enum-files)]
    (spit f (strip-parens (slurp f)))))
