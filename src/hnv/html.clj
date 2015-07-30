(ns hnv.html
  (:require [hiccup.page :refer [html5]]
            [hnv.core :as query]))

(def head
  [:head
    [:meta {:http-equiv "Content-Type" :content "text/html" :charset "UTF-8"}]
    [:title "Hnv"]
      [:link
        {:type "text/css" :rel "stylesheet" :href "/resources/css/materialize.min.css"
         :media "screen,projection"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]])

(def navbar
  [:nav {:class "light-blue lighten-1" :role "navigation"}
    [:div {:class "nav-wrapper container"}
      [:a {:id "logo-container" :href "/" :class "brand-logo"} "Hnv"]
      [:ul {:class "right hide-on-med-and-down"}
        [:li [:a {:href " "} [:i {:class "mdi-navigation-refresh"}]]]]
        ]])
      ;;[:ul {:id "nav-mobile" :class "side-nav"}
        ;;[:li [:a {:href " "} [:i {:class "mdi-navigation-refresh"}]]]]
      ;;[:a {:href "#" :data-activates "nav-mobile" :class "button-collapse"}
        ;;[:i {:class "material-icons"} "menu"]]]])

(def body1
  [:div {:class "section no-pad-bot" :id "index-banner"}
    [:div {:class "container"}
      [:br] [:br]
      [:h1 {:class "header center orange-text"} "Hnv"]
      [:div {:class "row center"}
        [:h5 {:class "header col s12 light"}
          "A simple HackerNews viewer"]]
      [:div {:class "row center"}
        [:a {:href "/news"
             :id "download-button"
             :class "btn-large waves-effect waves-light orange"}
             "Get Started"]]
      [:br] [:br]]])

(def body2
  [:div {:class "container"}
    [:div {:class "section"}
      [:div {:class "row"}
        [:div {:class "col s12 center"}
          [:h3 [:i {:class "mdi-content-send brown-text"}]]
          [:h4 "Introduction"]
          [:h5 {:class "light"} [:p]]
          [:h5 {:class "light"}
            "Hnv is read-only HackerNews Viewer and use HackerNews API."]]]]
    [:br] [:br]])

(def footer
  [:footer {:class "page-footer orange"}
    [:div {:class "container"}
      [:div {:class "row"}
        [:div {:class "col l6 s12"}
          [:h5 {:class "white-text"} "Company Bio"]
          [:p {:class "grey-text text-lighten-4"} "Bio"]]
        [:div {:class "col l3 s12"}
          [:h5 {:class "white-text"}"Settings"]
          [:ul
            [:li [:a {:class "white-text" :href "#!"} "Link 1"]]]]
        [:div {:class "col l3 s12"}
          [:h5 {:class "white-text"}"Connect"]
          [:ul
            [:li [:a {:class "white-text" :href "#!"} "Link 1"]]]]]]
    [:div {:class "footer-copyright"}
      [:div {:class "container"}
        "Made by "
        [:a {:class "orange-text text-lighten-3"
             :href "http://materializecss.com"}
             "devs"]]]])
(def top
  [:body
    [:script
      {:type "text/javascript"
       :src "https://code.jquery.com/jquery-2.1.1.min.js"}]
    [:script
      {:type "text/javascript"
       :src "/resources/js/materialize.min.js"}]
    navbar body1 body2 footer
    ])

(defn cardnize [[user score time title url]]
  [:div {:class "col s12 m6"}
    [:div {:class "card"}
      [:div {:class "card-content"}
        [:h5 title]
        [:p url]
        [:p (str score " points " " by " user  " ago ")]]
      [:div {:class "card-action"}
        [:a {:href url} "Source"]
        [:a {:href "#"} "More"]]]])

(defn card-join [x]
  (if (== 1 (count x))
    [:div {:class "row"} [:div {:class "container"} (first x)]]
    [:div {:class "row"} [:div {:class "container"} (first x) (second x)]]))

(defn pair-to-card [pair]
  (->> pair
    (map query/get-json)
    (map query/format-json)
    (map cardnize)
    (card-join)))

(defn card []
  (->> (query/get-topstory)
    (take 30)
    (partition-all 2)
    (map pair-to-card)))

(def news
  [:body
    [:script
      {:type "text/javascript"
       :src "https://code.jquery.com/jquery-2.1.1.min.js"}]
    [:script
      {:type "text/javascript"
       :src "/resources/js/materialize.min.js"}]
    navbar (card) footer])

(defn index []
  (html5 head top))

(defn viewer []
  (html5 head news))
