# language: nl

Functionaliteit: IT-addPersoon

    Achtergrond:
        Gegeven er zit 2 personen in de database.

    Scenario: Voeg een persoontoe aan de database.
        Als de client een nieuwe persoon toevoegt via "/personen"
        Dan moet de HTTP status code 200 zijn en huisnummer moet 88 zijn in de response.


        Gegeven er zit 2 personen in de database.

    Scenario: Het toevoegen van een persoon geeft een Exception, BSN bestaat reeds.
        Als de client een nieuwe persoon toevoegt via "/personen" met een bestaand BSN
        Dan moet de HTTP status code 400 zijn en de response is "BSN nummer: 899999999 bestaat reeds.".
