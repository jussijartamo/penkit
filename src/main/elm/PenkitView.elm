module PenkitView exposing (..)

import Model exposing (..)
import Html exposing (Html, text, div)
import Html.Attributes exposing (style)
import Material
import Material.Scheme
import Material.Button as Button
import Material.Options as Options exposing (css)
import Material.Typography as Typography
import Material.Textfield as Textfield
import Material.Slider as Slider
import Material.Grid as Grid
import Material.List as Lists
import Material.Color as Color
import Material.Snackbar as Snackbar

teams: List Team
teams = [ { name = "TIIMI1"
            , overallKg = 14005
            , recentlyKg = 640
            }
          , { name = "TIIMI2"
                , overallKg = 7505
                , recentlyKg = 34
                }
          , { name = "TIIMI3"
                , overallKg = 355
                , recentlyKg = 0
                }
          ]

toKg: Int -> List (Html b)
toKg kg =
    [text (toString kg)
    , Lists.subtitle [] [ text "kg" ]]

toAddedKg: Int -> List (Html b)
toAddedKg kg =
    [Options.span [css "font-weight" "bolder"
                 , Color.text Color.primary] [text ("+" ++ (toString kg))]]
{-
    div [] [text (toString kg)
            , Options.span [css "font-size" "smaller"
                            , css "vertical-align" "super"] [text "kg"]]
-}

team: Team -> Html b
team team =
    Lists.li [ Lists.withSubtitle ]
        [ Options.div
            [ Typography.title
            , css "text-align" "left"
            , css "width" "22rem"
            , css "margin-right" "2rem"
            ] [ text team.name ]
        , Lists.content
            [ css "text-align" "left"]
            (toKg team.overallKg)
        , Lists.content2
          []
          (toAddedKg team.recentlyKg)
          ]

status : Html Msg
status =
  Lists.ul [ css "margin" "0", css "padding" "0" ] (List.map team teams)

liftToText: Float -> String
liftToText lift =
    "NOSTA " ++ (toString (truncate lift)) ++ (if lift == (toFloat (truncate lift)) then " " else "½") ++ "KG!"

view : Model -> Html Msg
view model =
    div
        [ style [ ( "textAlign", "center" ) ] ]
        [ Options.styled Html.h4 [ Typography.headline ] [ text "PENKIT" ]
        , Textfield.render Mdl [0] model.mdl
            [ Textfield.label "NIMI!"
            , Textfield.floatingLabel
            , Textfield.text_
            ]
            []
        , Snackbar.view model.snackbar |> Html.map Snackbar
        , Options.styled Html.br [] []
        , Textfield.render Mdl [1] model.mdl
            [ Textfield.label "TIIMI!"
            , Textfield.floatingLabel
            , Textfield.text_
            ]
            []
        , Options.styled Html.br [] []
        , Grid.grid []
          [ Grid.cell
              [ Grid.size Grid.Tablet 8,
                Grid.size Grid.Desktop 6,
                Grid.offset Grid.Desktop 3]
              [ Options.span [] [text "RAUTAA KG!"]
              , Slider.view
                  [ Slider.onChange KgChange
                  , Slider.value model.lift
                  , Slider.min 20
                  , Slider.max 140
                  , Slider.step 2.5
                  ]
                , Options.styled Html.br [] []
                , Button.render Mdl
                  [2]
                  model.mdl
                  [ Button.ripple
                  , Button.colored
                  , Button.raised
                  , Options.onClick LiftClicked
                  , css "margin" "0 24px"
                  ]
                  [ text (liftToText model.lift) ]
                , status
                ]]
        ]
        |> Material.Scheme.top

viewMessage : String -> Html msg
viewMessage msg =
  div [] [ text msg ]