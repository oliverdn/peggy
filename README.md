# Lexer for parsing expression grammar (PEG)

A lexical analyzer takes as input a specification with a set of rules and corresponding actions.

## Syntax

`S = [a-z] "=" [0-9]+ ;`

*Each rule has a name (left-hand side) and an expression (right hand-side). It ends with semicolon.*

`"literal"`

*Match exact literal string.*

`[characters]`

*Match one character from a set.*

`rule`

*Nonterminal symbol.*

`expression +`

*Match one or more.*

`expression *`

*Match zero or more.*

`expression ?`

*Optional operator.*

`expression =`

*Lookahead operator. Try to match expression, then it succeeds if the match succeeds. It never consumes any input.*

`/`

*Choice operator. If no expression matches, consider the match failed.*

## Examples

Given this ruleset:

```
Inflection = Causative / Negative / Passive / PoliteNegative / PoliteVolitional ;
Causative = "させる" / "せる" / FormationA "せる"  / "せ" Inflection / FormationA "せ" Inflection ;
Continuative = "て" / "で" / "って" / "んで" / "うて" / "いて" / "いで" / "して" ;
Negative = "な" AdjVerb / FormationA "な" AdjVerb ;;
Passive = "られる" / "れる" ;
PoliteNegative = "ません" ;
PoliteVolitional = "ましょう" / FormationI "ましょう" / "しょう" ;
Provisional = "ければ" / "なきゃ" / "なくちゃ" ;
FormationA = "わ" / "か" / "が" / "さ" / "た" / "ま" / "ば" / "な" / "ら" ;;
FormationE = "え" / "け" / "げ" / "せ" / "て" / "め" / "べ" / "ね" / "れ" ;;
FormationI = "い" / "き" / "ぎ" / "し" / "ち" / "み" / "び" / "に" / "り" ;;
FormationO = "お" / "こ" / "ご" / "そ" / "と" / "も" / "ぼ" / "の" / "ろ" / "よ" ;;
AdjVerb = Provisional / "い" Continuative Inflection / "い" ;
```

`走りません` becomes `(:PoliteNegative "ません")`

`走らせられる` becomes `(:Causative "らせられる" (:FormationA "ら") (:_string "せ") (:Inflection "られる" (:Passive "られる")))`

`走りましょう` becomes `(:PoliteVolitional "りましょう" (:FormationI "り") (:_string "ましょう"))`

`走らなければ` becomes `(:Negative "らなければ" (:FormationA "ら") (:_string "な") (:AdjVerb "ければ" (:Provisional "ければ")))`

`走らないでしょう` becomes
```
(:Negative "らないでしょう" (:FormationA "ら") (:_string "な") (:AdjVerb "いでしょう"
(:_string "い") (:Continuative "で") (:Inflection "しょう" (:PoliteVolitional "しょう"))))
```