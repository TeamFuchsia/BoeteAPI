# language: nl

Functionaliteit: Integratie test

    Achtergrond:
        Gegeven er zitten 3 personen in de database.

    Scenario: Roep een endpoint aan
        Als de client een GETbyID request maakt naar "/personen/"
        Dan moet de HTTP status code 200 zijn en moet er 1 element in de response zitten
        En  the response is:
        """
        {
            "persoonnr": 1,
            "voornaam": "Fred",
            "achternaam": "Derf",
            "straat": "Fredstraat",
            "huisnummer": "10",
            "postcode": "1234 KL",
            "woonplaats": "Groningen",
            "bsn": "123456789",
            "geboortedatum": "01-01-2000"
        }
        """
