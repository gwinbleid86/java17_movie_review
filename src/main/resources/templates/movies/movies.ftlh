<#import "../layout.ftlh" as main>
<@main.layout>

    <div class="row justify-content-around">
        <#list movies.content as movie>
            <div class="card col-3 m-1">
                <h5 class="card-header">${movie.name}</h5>
                <div class="card-body">
                    <h5 class="card-title">${movie.director.fullName}</h5>
                    <p class="card-text">${movie.description}</p>
                    <a href="/${movie.id}" class="btn btn-primary">More info</a>
                </div>
            </div>
        </#list>
    </div>

    <a href="/add" class="btn btn-success">Create new movie</a>

</@main.layout>