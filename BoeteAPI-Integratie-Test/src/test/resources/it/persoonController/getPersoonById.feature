# language: nl

Functionaliteit: IT-getPersoonById

    Achtergrond:
        Gegeven er zitten 3 personen in de database.

    Scenario: Roep een endpoint aan
        Als de client een GETbyID request maakt naar "/personen/" met persoonnr
        Dan moet de HTTP status code 200 zijn en huisnummer moet 50 zijn in de response
