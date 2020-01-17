# language: nl

Functionaliteit: IT-addFeit

  Achtergrond:
    Gegeven er zit 3 feiten in de database.

  Scenario: Voeg een feit toe aan de database.
    Als de client een nieuwe feit toevoegt via "/feiten"
    Dan moet de HTTP status code 200 zijn en feitcode moet zijn "VBF-200"