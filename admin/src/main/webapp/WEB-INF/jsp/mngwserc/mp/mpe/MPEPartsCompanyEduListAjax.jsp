<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<tr>
    <th scope="row">${eduList1.cdNm}</th>
    <td class="text-center">${eduList1.year2024 + eduList1.year2023 + eduList1.year2022 + eduList1.year2021 + eduList1.year2020 + eduList1.yearElse}</td>
    <td class="text-center">${eduList1.year2024}</td>
    <td class="text-center">${eduList1.year2023}</td>
    <td class="text-center">${eduList1.year2022}</td>
    <td class="text-center">${eduList1.year2021}</td>
    <td class="text-center">${eduList1.year2020}</td>
    <td class="text-center">${eduList1.yearElse}</td>
</tr>
<tr>
    <th scope="row">${eduList2.cdNm}</th>
    <td class="text-center">${eduList2.year2024 + eduList2.year2023 + eduList2.year2021 + eduList2.year2020 + eduList2.yearElse}</td>
    <td class="text-center">${eduList2.year2024}</td>
    <td class="text-center">${eduList2.year2023}</td>
    <td class="text-center">${eduList2.year2022}</td>
    <td class="text-center">${eduList2.year2021}</td>
    <td class="text-center">${eduList2.year2020}</td>
    <td class="text-center">${eduList2.yearElse}</td>
</tr>
<tr>
    <th scope="row">${eduList3.cdNm}</th>
    <td class="text-center">${eduList3.year2024 + eduList3.year2023 + eduList3.year2022 + eduList3.year2021 + eduList3.year2020 + eduList3.yearElse}</td>
    <td class="text-center">${eduList3.year2024}</td>
    <td class="text-center">${eduList3.year2023}</td>
    <td class="text-center">${eduList3.year2022}</td>
    <td class="text-center">${eduList3.year2021}</td>
    <td class="text-center">${eduList3.year2020}</td>
    <td class="text-center">${eduList3.yearElse}</td>
</tr>
<tr>
    <th scope="row">${eduList4.cdNm}</th>
    <td class="text-center">${eduList4.year2024 + eduList4.year2023 + eduList4.year2022 + eduList4.year2021 + eduList4.year2020 + eduList4.yearElse}</td>
    <td class="text-center">${eduList4.year2024}</td>
    <td class="text-center">${eduList4.year2023}</td>
    <td class="text-center">${eduList4.year2022}</td>
    <td class="text-center">${eduList4.year2021}</td>
    <td class="text-center">${eduList4.year2020}</td>
    <td class="text-center">${eduList4.yearElse}</td>
</tr>
<tr>
    <th scope="row">소계</th>
    <td class="text-center" id="cumulativeCnt"></td>
    <td class="text-center">${eduList1.year2024 + eduList2.year2024 + eduList3.year2024 + eduList4.year2024}</td>
    <td class="text-center">${eduList1.year2023 + eduList2.year2023 + eduList3.year2023 + eduList4.year2023}</td>
    <td class="text-center">${eduList1.year2022 + eduList2.year2022 + eduList3.year2022 + eduList4.year2022}</td>
    <td class="text-center">${eduList1.year2021 + eduList2.year2021 + eduList3.year2021 + eduList4.year2021}</td>
    <td class="text-center">${eduList1.year2020 + eduList2.year2020 + eduList3.year2020 + eduList4.year2020}</td>
    <td class="text-center">${eduList1.yearElse + eduList2.yearElse + eduList3.yearElse + eduList4.yearElse}</td>
</tr>