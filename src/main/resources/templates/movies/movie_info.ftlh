<#import "../layout.ftlh" as main>
<@main.layout>
<#--    <div class="row">-->
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <input type="hidden" value="${movie.id}" id="movieId">
            <h5 class="card-title">${movie.name}</h5>
            <h6 class="card-subtitle mb-2 text-muted">${movie.director.fullName}</h6>
            <h6 class="card-subtitle mb-2 text-muted">${movie.releaseYear?string.number}</h6>
            <p class="card-text">${movie.description}</p>
            <hr>
            <h6 class="card-subtitle mb-2 text-muted">Cast:</h6>
            <#list movie.castMembers as item>
                <p class="card-text">${item.fullName} - ${item.role}</p>
            </#list>
        </div>
    </div>
<#--    </div>-->

    <hr>

    <div id="review_block">
        <form id="review_form">
            <div class="form-group">
                <label for="rating">Rating</label>
                <select class="form-control" id="rating" name="rating">
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </div>
            <div class="form-group">
                <label for="comment">Comment</label>
                <textarea class="form-control" id="comment" rows="3" name="comment"></textarea>
            </div>
            <button type="submit" class="btn btn-success">Success</button>
        </form>
    </div>
    <script>
        const reviewForm = document.getElementById('review_form')
        const baseUrl = 'http://localhost:8089'
        const movieId = document.getElementById('movieId').getAttribute('value');

        const user = {
            email: 'qwe@qwe.qwe',
            password: 'qwe'
        }
        const headers = new Headers()
        headers.set('Authorization', 'Basic ' + btoa(user.email + ":" + user.password))
        headers.set("Content-Type", "application/json")

        function reviewHandler(e) {
            e.preventDefault()
            let form = e.target
            let data = new FormData(form)


            data.append('movieId', movieId)


            let json = JSON.stringify(Object.fromEntries(data))

            fetch(
                baseUrl + '/reviews',
                {
                    method: 'POST',
                    headers: headers,
                    body: json
                }
            )
        }

        reviewForm.addEventListener('submit', reviewHandler)


        window.addEventListener('load', async () => {
            setInterval(async () => {
                let response = JSON.stringify(await getComments())
                let review = JSON.parse(response)
                fillBlock(review)
            }, 10000)
        })

        async function getComments() {
            let response = await fetch(
                baseUrl + `/reviews/${movieId}`,
                {
                    method: 'GET',
                    headers: headers
                }
            )
            return await response.json()
        }

        function fillBlock(review) {
            let reviewBlock = document.getElementById('review_block')
            let reviewHeaders = document.getElementsByClassName('review-card')
            let lastElement = Array.from(reviewHeaders).pop()
            for (let reviewElement of review) {
                let lastReviewDateTime
                let createTime = new Date(reviewElement.createTime)
                if (!lastElement) {
                    createBlock(reviewElement, reviewBlock);
                } else {
                    let value = lastElement.getElementsByTagName('i')[0].innerText
                    lastReviewDateTime = new Date(value);
                }

                if (compareDates(createTime, lastReviewDateTime)) {
                    createBlock(reviewElement, reviewBlock)
                }
            }
        }

        function compareDates(d1, d2) {
            return d1.getTime() > d2.getTime()
        }

        function createBlock(reviewElement, reviewBlock) {
            // <div class="card">
            let card = document.createElement('div')
            card.setAttribute('class', 'card my-3 review-card')

            // <div class="card-header">
            let cardHeader = document.createElement('div')
            cardHeader.setAttribute('class', 'card-header')
            cardHeader.innerText = reviewElement.reviewer

            card.append(cardHeader)

            // <div class="card-body">
            let cardBody = document.createElement('div')
            cardBody.setAttribute('class', 'card-body')

            // <blockquote class="blockquote mb-0">
            let blockquote = document.createElement('blockquote')
            blockquote.setAttribute('class', 'blockquote mb-0')
            cardBody.append(blockquote)
            let p = document.createElement('p')
            p.innerText = reviewElement.comment
            cardBody.append(p)


            //<footer class="blockquote-footer">
            let footer = document.createElement('footer')
            footer.setAttribute('class', 'blockquote-footer')
            let dateTime = new Date(reviewElement.createTime)
            let i = document.createElement('i')
            i.setAttribute('style', 'display:none')
            i.innerText = reviewElement.createTime
            cardBody.append(i)
            footer.innerText = dateTime.toString()
            cardBody.append(footer)

            card.append(cardBody)

            reviewBlock.append(card)
        }
    </script>
</@main.layout>