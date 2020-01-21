# language: nl

Functionaliteit: IT-updatePersoonById

  Achtergrond:
    Gegeven er zitten 2 personen in de database!

  Scenario: Update een persoon door het BSN nummer te wijzigen
    Als de client een PutbyID request maakt naar "/personen/" met persoonnr
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
    Dan moet de HTTP status code 200 zijn en bevat:
      | achternaam | Anders |

  Scenario: Update een persoon door een niet bestaand persoonr mee te geven in de invoer
    Als de client een PutbyID request maakt naar "/personen/0"
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
      | error | Persoonnummer: 0 bestaat niet! |

  Scenario: Update een persoon door een BSN nummer in een bestaand BSN nummer te wijzigen
    Als de client een PutbyID request maakt naar "/personen/" met persoonnr
          """
        {
                "voornaam": "Hans",
                "achternaam": "Anders",
                "straat": "Kerkstraat",
				"huisnummer": "88",
				"postcode": "2201 EB",
				"woonplaats": "Leeuwarden",
				"bsn": "900000000",
				"geboortedatum": "02-12-1958"
        }
        """
    Dan moet de HTTP status code 400 zijn en bevat:
      | error | BSN nummer: 900000000 bestaat reeds. |