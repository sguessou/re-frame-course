(ns re-frame-course.components.forms
  (:require [reagent.core :as reagent]
            [secretary.core :as s]))


(defn greeting [txt]
  [:h1 txt])

(defn counting-button [txt]
  (let [state (reagent/atom 0)]
    (fn [txt]
      [:button
       {:on-click (fn [e] (swap! state inc))}
       (str txt " -- " @state)])))

(defn canvas []
  (let [size (reagent/atom 10)
        id (js/setInterval
            (fn []
              (swap! size (fn [s]
                            (-> s
                                inc
                                (mod 200)))))
            100)]
    (reagent/create-class
     {:component-did-mount
      (fn [comp]
        (let [node (reagent/dom-node comp)
              ctx (.getContext node "2d")]
          (.clearRect ctx 0 0 200 200)
          (.fillRect ctx 10 10 @size @size)))
      
      :reagent-render
      (fn []
        @size
        [:canvas {:style
                  {:width 200
                   :height 200
                   :border "1px solid green"}}])

      :component-did-update
      (fn [comp]
        (let [node (reagent/dom-node comp)
              ctx (.getContext node "2d")]
          (.clearRect ctx 0 0 200 200)
          (.fillRect ctx 10 10 @size @size)))

      :component-will-unmount
      (fn [comp]
        (js/clearInterval id))})))

(defn components-panel []
  [:<>
   [greeting "Components"]
   [:p [:a {:href "#"}"Back"]]
   [counting-button "Click me"]
   [:div
    [canvas]]])

