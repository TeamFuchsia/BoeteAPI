# language: nl

Functionaliteit: IT-getPersoonById

    Achtergrond:
        Gegeven er zitten 3 personen in de database.

    Scenario: Vraag een persoon op uit de database
        Als de client een GETbyID request maakt naar "/personen/" met persoonnr
        Dan moet de HTTP status code 200 zijn en bevat:
            | achternaam           | Derf     |

    Scenario: Vraag een persoon op uit de database
        Als de client een GETbyID request maakt naar "/personen/0"
        Dan moet de HTTP status code 400 zijn en bevat:
            | error          | PersoonNummer: 0 bestaat niet     |