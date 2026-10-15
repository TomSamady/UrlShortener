const urlInput = document.getElementById("urlInput");
const shortenButton = document.getElementById("shortenButton");
const shortenForm = document.getElementById("shortenForm");
const result = document.getElementById("result");

shortenForm.addEventListener("submit", async function (event) 
{
    event.preventDefault();
    const url = urlInput.value.trim();

    if(url === "")
    {
        result.textContent = "Please enter a URL.";
        return;
    }

    shortenButton.disabled = true;
    shortenButton.textContent = "Shortening...";
    result.textContent = "";

    try
    {
        const response = await fetch("/shorten", {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                url: url
            })
        });

        if(response.ok)
        {
            const data = await response.json();

            const shortCode = new URL(data.shortUrl).pathname.substring(1);

            result.innerHTML = 
            `
                <p> Shortened URL: </p>
                <div class="shortUrlBox">
                    <a href = "${data.shortUrl}" target="_blank">
                        ${data.shortUrl}
                    </a>

                    <button id="copyButton">
                        Copy
                    </button>
                </div>

                <div id="stats"></div>

                <button id="refreshStatsButton">
                    Refresh Stats
                </button>
            `;

            const copyButton = document.getElementById("copyButton");
            const statsDiv = document.getElementById("stats");
            const refreshStatsButton = document.getElementById("refreshStatsButton");

            copyButton.addEventListener("click", async function () 
            {
                await navigator.clipboard.writeText(data.shortUrl);
                copyButton.textContent = "Copied";

                setTimeout(function()
                {
                    copyButton.textContent = "Copy";
                }, 2000);
            });

            async function loadStats()
            {
                const statsResponse = await fetch(`/stats/${shortCode}`);

                if(statsResponse.ok)
                {
                    const stats = await statsResponse.json();

                    statsDiv.innerHTML=`
                        <p><strong>Original URL:<strong> ${stats.originalUrl}</p>
                        <p><strong>Clicks:<strong> ${stats.clickCount}</p>
                `;
                }
                else
                {
                    statsDiv.textContent = "Unable to load stats.";
                }

            }
            loadStats();

            refreshStatsButton.addEventListener("click", function ()
            {
                loadStats();
            });
        }
        else 
        {

            const error = await response.text();

            result.textContent = error;

        }
    }
    catch(error)
    {
        result.textContent = "Something went wrong. Please try again.";
    }
    finally
    {
        shortenButton.disabled = false;
        shortenButton.textContent = "Shorten URL";
    }
});