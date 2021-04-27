# lit-transport-nba-scrape

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/049a11c9272042229582d06cbe6a46ee)](https://app.codacy.com/gh/QuantumlyTangled/lit-transport-nba-scrape?utm_source=github.com&utm_medium=referral&utm_content=QuantumlyTangled/lit-transport-nba-scrape&utm_campaign=Badge_Grade_Settings)
[![codecov](https://codecov.io/gh/QuantumlyTangled/lit-transport-nba-scrape/branch/main/graph/badge.svg?token=rinYV4MjRK)](https://codecov.io/gh/QuantumlyTangled/lit-transport-nba-scrape)

This is my attempt at the take-home project provided by Lit-Transport.
The instructions can be found [here](./LIT%20programming%20task_Java.pdf) though I was instructed to use [Basketball reference](https://www.basketball-reference.com/leagues/NBA_2020_per_game.html) instead of [NBA stats](https://www.nba.com/stats/).

```shell
$ nba Luka Doncic
Season 2018-19 - 3PA 7.1
Season 2019-20 - 3PA 8.9
Season 2020-21 - 3PA 8.3
```

## Notes
* The displayed data is scraped from the "Per Game" table on the players profile.
* The CLI tries to cover as many edge cases as possible but alas each time I solve one I find another.
