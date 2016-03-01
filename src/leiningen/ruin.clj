(ns leiningen.ruin
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.java.shell :refer [sh]]))

(defn- walk [dirpath pattern]
  (doall (filter #(re-matches pattern (.getName %))
                 (file-seq (io/file dirpath)))))

(defn- enum-files []
  (map #(.getPath %) (walk "src" #".*\.clj.*")))

(defn- strip-parens [file-string]
  (str/replace file-string #"(\(|\)|\[|\]|\{|\})" ""))

(defn- git? []
  (.exists (io/file ".git")))

(defn- commit []
  (do
    (sh "git" "add" ".")
    (sh "git" "commit" "-m" "RUINATION")))

(defn ruin
  "Ruin your project."
  [project & args]
  (doseq [f (enum-files)]
    (spit f (strip-parens (slurp f))))
  (when (git?)
    (commit)))
