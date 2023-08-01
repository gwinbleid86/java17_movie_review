<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row">
        <div class="card" style="width: 18rem;">
            <div class="card-body">
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
    </div>
</@main.layout>