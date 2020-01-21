# language: nl

Functionaliteit: IT-addPersoon

  Achtergrond:
    Gegeven er zit 2 personen in de database.

  Scenario: Voeg een persoon toe aan de database.
    Als de client een POST maakt naar "/personen" met
        """
        {
            "voornaam": "Hans",
            "achternaam": "Anders",
            "straat": "Kerkstraat",
            "huisnummer": "88",
            "postcode": "2201 EB",
            "woonplaats": "Leeuwarden",
            "bsn": "777654111",
            "geboortedatum": "02-12-1958"
        }
        """
    Dan moet de HTTP status code 200 zijn en bevat:
      | bsn | 777654111 |

  Scenario: Het toevoegen van een persoon geeft een Exception, BSN bestaat reeds.
    Als de client een POST maakt naar "/personen" met
        """
        {
                "voornaam": "Hans",
                "achternaam": "Anders",
                "straat": "Kerkstraat",
				"huisnummer": "88",
				"postcode": "2201 EB",
				"woonplaats": "Leeuwarden",
				"bsn": "899999999",
				"geboortedatum": "02-12-1958"
        }
        """
    Dan moet de HTTP status code 400 zijn en bevat:
      | error | BSN nummer: 899999999 bestaat reeds. |

  Scenario: Het toevoegen van een persoon geeft een Exception, BSN bestaat reeds.
    Als de client een POST maakt naar "/personen" met
        """
        {
                "voornaam": "",
                "achternaam": "Anders",
                "straat": "Kerkstraat",
				"huisnummer": "88",
				"postcode": "2201 EB",
				"woonplaats": "Leeuwarden",
				"bsn": "199999999",
				"geboortedatum": "02-12-1958"
        }
        """
    Dan moet de HTTP status code 400 zijn en bevat:
      | error | Voornaam moet ingevuld zijn |