# language: nl

Functionaliteit: IT-updatePersoonById

    Achtergrond:
        Gegeven er zit 2 persoon in de database.

    Scenario: Update een persoon door het BSN nummer te wijzigen
        Als de client een PUT updatePersoonById met een "goede" invoer maakt naar "/personen/"
        Dan moet de HTTP status code 200 zijn en moet in de response o.a het gewijzigde BSN nummer 112233445 zitten

#        Gegeven er zit 1 persoon in de database.

    Scenario: Update een persoon door geen persoonnr mee te geven in de invoer
        Als de client een PUT updatePersoonById met een "geen persoonnr" invoer maakt naar "/personen/"
        Dan moet de HTTP status code 400 zijn en de response "Geen Persoonnummer ingevoerd" bevat.

    Scenario: Update een persoon door een niet bestaand persoonr mee te geven in de invoer
        Als de client een PUT updatePersoonById met een "fout persoonnr" invoer maakt naar "/personen/"
        Dan moet de HTTP status code 400 zijn en de response "Persoonnummer: 0 bestaat niet!" bevat.

    Scenario: Update een persoon door een BSN nummer in een bestaand BSN nummer te wijzigen
        Als de client een PUT updatePersoonById met een "bestaand BSN" invoer maakt naar "/personen/"
        Dan moet de HTTP status code 400 zijn en de response "BSN nummer: 900000000 bestaat reeds." bevat.
