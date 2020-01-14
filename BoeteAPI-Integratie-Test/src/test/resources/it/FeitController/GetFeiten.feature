# language: nl

Functionaliteit: Integratie test

  Achtergrond:
    Gegeven er zitten 4 personen in de database

  Scenario: Roep een endpoint aan
    Als de client een GET request maakt naar "/feiten"
    Dan moet de HTTP status code 200 zijn en moeten er 4 elementen in de response zitten