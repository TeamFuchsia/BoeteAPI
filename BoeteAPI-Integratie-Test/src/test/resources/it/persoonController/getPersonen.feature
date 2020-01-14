# language: nl

Functionaliteit: Integratie test

    Achtergrond:
        Gegeven er zitten 3 personen in de database

    Scenario: Roep een endpoint aan
        Als de client een GET request maakt naar "/personen"
        Dan moet de HTTP status code 200 zijn en moet er 1 payload in de response zitten
