# language: nl

Functionaliteit: IT-addFeit

  Achtergrond:
    Gegeven er zit 3 feiten in de database.

  Scenario: Voeg een feit toe aan de database.
    Als de client een nieuwe feit toevoegt via "/feiten"
    Dan moet de HTTP status code 200 zijn en feitcode moet zijn "VBF-200"

  Scenario: Voeg nieuwe feit met bestaande feitcode toe aan de database.
    Als de client een nieuwe feit toevoegt met een bestaande feitcode via "/feiten"
    Dan moet de HTTP status code 400 zijn met error response "Feitcode: VBF-010 bestaat al in de database."

    Scenario: Voeg een feit met een lege omschrijving toe aan de database.
      Als de client een nieuwe feit toevoegt zonder omschrijving via "/feiten"
      Dan moet de HTTP status code 400 zijn met error response: "Omschrijving mist,  voeg deze nog toe"