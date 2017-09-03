# PENKIT

## INSTALLING
```
elm install
```
You can install `elm` and `elm-live` with npm:
```
npm i elm elm-live -g
```

## RUNNING (DEV)

```
elm-live --open
```

## COMPILING

```
elm-make src/main/elm/penkit.elm --output src/main/resources/public/elm.js
```

## MINIFY

Install `uglifyjs`
```
npm i -g uglify-js
```

```
uglifyjs src/main/resources/public/elm.js --compress --mangle --output src/main/resources/public/elm-min.js
```

### TODO

TODO
- Use connection pool such as HikariCP
- Figure out mobile usable `insert kg` component

DONE
- Stress test insert on same user and on many users at same time
- Test Spark Websocket with Elm

