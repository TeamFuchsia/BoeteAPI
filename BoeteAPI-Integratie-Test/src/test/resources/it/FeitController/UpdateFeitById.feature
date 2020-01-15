# language: nl

Functionaliteit: Integratie test

  Achtergrond:
    Gegeven er zitten 4 feiten in de database

  Scenario: Roep een endpoint aan
    Als de client een PUT request maakt naar "/feiten/1"
    Dan moet de HTTP status code 200 zijn en moeten er 1 elementen in de response zitten