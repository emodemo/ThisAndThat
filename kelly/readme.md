# Kelly Criterion

* 1 bet with 2+ outcomes having different probabilities and payoofs to win/lose => find optiomal f* to bet.
* 1 asset with PDF of returns over a time and some constraints => find optimal f* to bet
* 2+ assets with PDFs of returns => how much to bet on each one => portfolio distribution.
* 2+ assets with dependencies between them => how much to bet on each one => portfolio distribution.

## Others

* Kullback-Leibler divergence - information gain between 2 proposed distributions
* Akaike’s information criterion
* Hidden moments
* mutual information instead of covariance or correlation - check what happens on negative / positive / no correlation
* Memoryless distributions
  * discrete - Geometric
  * continuous - Exponential

## Statistics notes

* Descriptive statistics, where $T$ is wealth in horizon, given discrete returns $X_t$ + see Stable Distributions in wiki
  * mean $\mu \ * \ T$
  * median, mode ...
  * std $\sigma \ * \ T^{1/2}$
  * skewness
  * kurtosis
  * stability parameter - $\alpha$ : 2 for Gaussian, 1.7 for stable, 1 for Cauchy, ?? for Student t (this one is symmetric)
  * scale parameter $c$ : the larger the more spread-out the distribution
  * skewness parameter : $\beta$
  * scale : $\gamma \ * \ T^{1/\alpha}$ 
  * shift : $\delta \ * \ T$
* Copulas: with different distributions (KDEstimator)
* Tail estimator => Hill estimators, Power Law alpha

## Simulations

* Drawdown of portfolio, compared with profit and Kelly fraction
* Drawdown of ETF/Stock