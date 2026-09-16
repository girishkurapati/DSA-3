let last = null;

let step = 0;

let timer = null;

let playing = false;


const $ = id =>
    document.getElementById(id);


/* -------------------------
   READ INPUT
------------------------- */

function getDimensions() {

    const values =
        $("dims")
        .value
        .trim()
        .split(/[,xX\s]+/)
        .filter(Boolean)
        .map(Number);


    if (
        values.length < 2 ||
        values.some(
            x =>
                !Number.isInteger(x) ||
                x <= 0
        )
    ) {

        throw new Error(
            "Use positive integer dimensions, e.g. 10, 30, 5, 60."
        );

    }


    return values;
}


/* -------------------------
   CALL JAVA BACKEND
------------------------- */

async function optimize() {

    try {

        const dimensions =
            getDimensions();


        const response =
            await fetch(
                "/api/optimize",
                {

                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify({
                            dimensions
                        })

                }
            );


        if (!response.ok) {

            throw new Error(
                "Java backend error."
            );

        }


        last =
            await response.json();


        renderResults(last);


        step = 0;

        renderStep();


        toast(
            "Java Interval DP completed ✓"
        );


    } catch (error) {

        toast(error.message);

    }

}


/* -------------------------
   DISPLAY RESULTS
------------------------- */

function renderResults(result) {

    $("oc").textContent =
        formatNumber(
            result.optimizedCost
        );


    $("nc").textContent =
        formatNumber(
            result.naiveCost
        );


    $("sv").textContent =
        formatNumber(
            result.saved
        );


    $("eg").textContent =
        result.efficiency.toFixed(2)
        + "%";


    $("order").textContent =
        result.parenthesization;


    renderTable(result);

}


/* -------------------------
   DP TABLE
------------------------- */

function renderTable(result) {

    const n =
        result.matrixCount;


    let html =
        "<tr><th>i\\j</th>";


    for (
        let j = 1;
        j <= n;
        j++
    ) {

        html +=
            "<th>A" +
            j +
            "</th>";

    }


    html += "</tr>";


    for (
        let i = 1;
        i <= n;
        i++
    ) {

        html +=
            "<tr><th>A" +
            i +
            "</th>";


        for (
            let j = 1;
            j <= n;
            j++
        ) {

            let value;


            if (j < i) {

                value = "—";

            }

            else if (i === j) {

                value = "0";

            }

            else {

                value =
                    formatNumber(
                        result.dp[i][j]
                    );

            }


            html +=
                "<td>" +
                value +
                "</td>";

        }


        html += "</tr>";

    }


    $("table").innerHTML =
        html;

}


/* -------------------------
   3D ANIMATION
------------------------- */

function renderStep() {

    if (!last) return;


    const total =
        last.matrixCount + 1;


    const descriptions = [

        "Ready",

        "Selecting optimal interval",

        "Moving matrices into the operation zone",

        "Multiplying selected matrices",

        "Merging the result",

        "Continuing optimal order",

        "Final optimized matrix"

    ];


    const index =
        Math.min(
            step,
            descriptions.length - 1
        );


    $("stepNo").textContent =
        "STEP " + step;


    $("stepLabel").textContent =
        descriptions[index];


    $("stageText").textContent =
        step === 0

        ?

        "Press PLAY to watch the multiplication sequence."

        :

        "Step " +
        step +
        " of " +
        total +
        " • " +
        descriptions[index];


    const percentage =
        Math.min(
            100,
            (step / total) * 100
        );


    $("progress").style.width =
        percentage + "%";


    const matrices =
        document.querySelectorAll(
            ".matrix"
        );


    matrices.forEach(
        (matrix, index) => {

            matrix.classList.remove(
                "active",
                "merged"
            );


            if (
                index < step - 1
            ) {

                matrix.classList.add(
                    "merged"
                );

            }

            else if (
                index ===
                Math.min(
                    step - 1,
                    4
                )
            ) {

                matrix.classList.add(
                    "active"
                );

            }

        }
    );

}


/* -------------------------
   PLAY / PAUSE
------------------------- */

function playAnimation() {

    if (!last) {

        toast(
            "Run optimization first."
        );

        return;

    }


    if (playing) {

        clearInterval(timer);

        playing = false;

        $("play").textContent =
            "▶ PLAY";

        return;

    }


    playing = true;

    $("play").textContent =
        "Ⅱ PAUSE";


    timer =
        setInterval(
            () => {

                step++;


                if (
                    step >
                    last.matrixCount + 1
                ) {

                    step = 0;

                }


                renderStep();

            },

            1100
        );

}


/* -------------------------
   NEXT
------------------------- */

function nextStep() {

    if (!last) return;


    step =
        Math.min(
            step + 1,
            last.matrixCount + 1
        );


    renderStep();

}


/* -------------------------
   PREVIOUS
------------------------- */

function previousStep() {

    if (!last) return;


    step =
        Math.max(
            0,
            step - 1
        );


    renderStep();

}


/* -------------------------
   RESET
------------------------- */

function resetAnimation() {

    clearInterval(timer);

    playing = false;

    $("play").textContent =
        "▶ PLAY";


    step = 0;

    renderStep();

}


/* -------------------------
   RANDOM INPUT
------------------------- */

function randomize() {

    const count =
        4 +
        Math.floor(
            Math.random() * 3
        );


    const values = [];


    for (
        let i = 0;
        i < count + 1;
        i++
    ) {

        values.push(
            8 +
            Math.floor(
                Math.random() * 53
            )
        );

    }


    $("dims").value =
        values.join(", ");


    optimize();

}


/* -------------------------
   NUMBER FORMAT
------------------------- */

function formatNumber(number) {

    return Number(number)
        .toLocaleString(
            "en-US"
        );

}


/* -------------------------
   TOAST
------------------------- */

function toast(message) {

    const element =
        document.createElement(
            "div"
        );


    element.textContent =
        message;


    element.style.cssText = `

        position: fixed;

        right: 18px;

        bottom: 18px;

        background: #0c2d44;

        border: 1px solid #28d8ff;

        color: #eaffff;

        padding: 10px 14px;

        border-radius: 7px;

        font: 11px Arial;

        z-index: 20;

    `;


    document.body.appendChild(
        element
    );


    setTimeout(
        () => element.remove(),
        2200
    );

}


/* -------------------------
   BUTTON EVENTS
------------------------- */

$("opt")
    .addEventListener(
        "click",
        optimize
    );


$("random")
    .addEventListener(
        "click",
        randomize
    );


$("play")
    .addEventListener(
        "click",
        playAnimation
    );


$("next")
    .addEventListener(
        "click",
        nextStep
    );


$("prev")
    .addEventListener(
        "click",
        previousStep
    );


$("reset")
    .addEventListener(
        "click",
        resetAnimation
    );


$("dims")
    .addEventListener(
        "keydown",
        event => {

            if (
                event.key ===
                "Enter"
            ) {

                optimize();

            }

        }
    );


/* Automatically calculate */

optimize();