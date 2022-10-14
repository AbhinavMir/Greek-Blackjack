# Design Document


## 1. Considerations

#### 1.1 Constraints
The implementation requires a variant of Blackjack called Trianta Ena. There are some changes in rules and card distribution method. 

#### 1.2 Quick testing
To make sure we can quickly iterate and set up a game, I use command line arguments. For a quick spin, you can run `./start.sh` in the source file. For fine tuning the parameter, the template is as follows.

```javascript
java Main <NUMBER OF PLAYERS> <MINIMUM BET> <BALANCE FOR ALL PLAYERS> <NAME OF PLAYERS (divided by space)>
```

## 2. Architecture

<img src = "./uml.png"  style="width:400px;"/>

#### 2.1 Overview

The deck is a class with subclass card (although it is not implemented as a direct subclass). The card has multiple members - an enum for state, suit and value. An arrayList of the card results in both deck and hand - and the card is then transfered between these two for logistics. The rules are implemented over simple loops - I avoided using the `ruleSet` interface from ticTacToe since the rules are differently implemented.

#### 2.5 Deployment Diagrams

*Provide here the deployment diagram for the system including any information needed to describe it. Also, include any information needed to describe future scaling of the system.*

#### 2.6 Other Diagrams
*Provide here any additional diagrams and their descriptions in subsections.*

## 3 User Interface Design
*Provide here any user interface mock-ups or templates. Include explanations to describe the screen flow or progression.*

## 4 Appendices and References


#### 4.1 Definitions and Abbreviations
*List here any definitions or abbreviations that could be used to help a new team member understand any jargon that is frequently referenced in the design document.*

#### 4.2 References
*List here any references that can be used to give extra information on a topic found in the design document. These references can be referred to using superscript in the rest of the document.*