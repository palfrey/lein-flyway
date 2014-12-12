(ns leiningen.flyway
  (:require [leiningen.core.eval :refer [eval-in-project]]))

(defn flyway
  "Run the flyway plugin."
  {:help-arglists '([clean baseline migrate info validate repair])}
  [project subtask]
  (let [config (:flyway project)
        subtask-keyword (keyword subtask)]
    (eval-in-project
     (update-in project [:dependencies] conj ['com.github.metaphor/lein-flyway "3.1-SNAPSHOT"])
     `(do
        (let [flyway# (fw/flyway ~config)]
          (case ~subtask-keyword
            :clean (fw/clean flyway#)
            :baseline (fw/baseline flyway#)
            :validate (fw/validate flyway#)
            :migrate (fw/migrate flyway#)
            :info (fw/info flyway#)
            :repair (fw/repair flyway#))))
     '(require '[flyway.flyway :as fw]))))
