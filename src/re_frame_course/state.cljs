(ns re-frame-course.state
  (:require [reagent.core :as reagent]))

(def app-state (reagent/atom {:page :home}))
